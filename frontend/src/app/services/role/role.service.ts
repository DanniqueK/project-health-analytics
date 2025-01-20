import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, of, firstValueFrom } from "rxjs";
import { catchError, map, timeInterval } from "rxjs/operators";
import { AuthenticationService } from "../authentication/authentication.service";
import { environment } from "../../../environments/environment";

@Injectable({
	providedIn: "root"
})
export class RoleService {
	constructor(
		private http: HttpClient,
		private authenticationService: AuthenticationService
	) {}
	private apiUrl = `${environment.API}/user/role`;

	/**
	 * Gets the role of the current user.
	 *
	 * @returns {Observable<string>} - An observable that emits the role of the user.
	 */
	getUserRole(): Observable<string> {
		return this.http.get<{ userType: string }>(this.apiUrl, { withCredentials: true }).pipe(
			map(response => {
				const userType = response.userType;
				if (userType === "patient") {
					this.authenticationService.setPatientAuthenticated(true);
				} else if (userType === "medical_professional") {
					this.authenticationService.setMedicalProfessionalAuthenticated(true);
				}
				return userType;
			}),
			catchError(error => {
				return of("none");
			})
		);
	}

	/**
	 * Checks if the user is authorized based on the required roles.
	 *
	 * @param {string[]} requiredRoles - The roles required to access a resource.
	 * @returns {Promise<boolean>} - Returns a promise that resolves to true if the user is authorized, otherwise false.
	 */
	async isAuthorized(requiredRoles: string[]): Promise<boolean> {
		try {
			const userRole = await firstValueFrom(this.getUserRole());
			return requiredRoles.includes(userRole);
		} catch {
			return false;
		}
	}
}
