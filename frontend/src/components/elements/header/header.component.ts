import { Component, OnInit } from "@angular/core";
import { Router, NavigationEnd } from "@angular/router";
import { filter } from "rxjs/operators";
import { RoleService } from "../../../services/role/role.service";
import { MatIconModule } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { RouterLink, RouterLinkActive } from "@angular/router";
import { AuthenticationService } from "../../../services/authentication/authentication.service";
import { CommonModule } from "@angular/common";
import { PatientService } from "../../../services/patient/patient.service";
import { environment } from "../../../../environments/environment";
import { LogoutService } from "../../../services/logout.service";

@Component({
	selector: "app-header",
	standalone: true,
	imports: [MatIconModule, MatMenuModule, RouterLink, RouterLinkActive, CommonModule],
	templateUrl: "./header.component.html",
	styleUrls: ["./header.component.css"]
})
export class HeaderComponent implements OnInit {
	private currentUrl: string = "";
	public patientName: string = environment.MESSAGES.NO_PATIENT_SELECTED;

	constructor(
		public roleService: RoleService,
		private router: Router,
		public authenticationService: AuthenticationService,
		private patientService: PatientService,
		private logoutService: LogoutService
	) {
		// Subscribe to router events
		this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe((event: any) => {
			if (event.url === "/mp-portal") {
				// Just clear the selected patient through the service
				this.patientService.setSelectedPatient({ bsn: 0, name: "" });
			}
		});
	}

	ngOnInit() {
		this.patientService.selectedPatient$.subscribe(patient => {
			this.patientName = patient?.name || environment.MESSAGES.NO_PATIENT_SELECTED;
		});
	}

	/**
	 * Logs out the user by calling the logout service.
	 * Resets the authentication state and navigates to the login page.
	 */
	onLogout(): void {
		this.logoutService.logOut().subscribe({
			next: () => {
				this.authenticationService.setUserNotAuthenticated(); // Reset auth state
				this.router.navigate(["/log-in"]); // Navigate to the login page
			}
		});
	}
}
