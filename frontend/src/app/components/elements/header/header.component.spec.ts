import { TestBed, ComponentFixture } from "@angular/core/testing";
import { provideRouter } from "@angular/router";
import { MatIconModule } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { Router } from "@angular/router";
import { HttpClientModule } from "@angular/common/http";
import { HeaderComponent } from "./header.component";
import { RoleService } from "../../../services/role/role.service";
import { AuthenticationService } from "../../../services/authentication/authentication.service";
import { PatientService } from "../../../services/patient/patient.service";
import { BehaviorSubject } from "rxjs";

/**
 * Mock service for AuthenticationService to simulate authentication states.
 */
class MockAuthenticationService {
	private medicalProfessionalSubject = new BehaviorSubject<boolean>(false);
	private patientSubject = new BehaviorSubject<boolean>(false);

	/**
	 * Sets the medical professional authentication state.
	 * @param {boolean} value - The authentication state to set.
	 */
	setMedicalProfessional(value: boolean) {
		this.medicalProfessionalSubject.next(value);
	}

	/**
	 * Sets the patient authentication state.
	 * @param {boolean} value - The authentication state to set.
	 */
	setPatient(value: boolean) {
		this.patientSubject.next(value);
	}

	/**
	 * Returns an observable for the medical professional authentication state.
	 * @returns {Observable<boolean>} - The observable for the medical professional authentication state.
	 */
	authenticateAsMedicalProfessional() {
		return this.medicalProfessionalSubject.asObservable();
	}

	/**
	 * Returns an observable for the patient authentication state.
	 * @returns {Observable<boolean>} - The observable for the patient authentication state.
	 */
	authenticateAsPatient() {
		return this.patientSubject.asObservable();
	}
}

/**
 * Mock service for RoleService to simulate user roles.
 */
class MockPatientRoleService {
	private roleSubject = new BehaviorSubject<string>("patient");

	/**
	 * Returns an observable for the user role.
	 * @returns {Observable<string>} - The observable for the user role.
	 */
	getUserRole() {
		return this.roleSubject.asObservable();
	}
}

describe("HeaderComponent - Navigation Routing", () => {
	let fixture: ComponentFixture<HeaderComponent>;
	let component: HeaderComponent;
	let router: Router;
	let mockAuthenticationService: MockAuthenticationService;

	/**
	 * Sets up the testing module and initializes the component and services.
	 */
	beforeEach(async () => {
		mockAuthenticationService = new MockAuthenticationService();

		await TestBed.configureTestingModule({
			imports: [HeaderComponent, MatIconModule, MatMenuModule, HttpClientModule],
			providers: [
				provideRouter([{ path: "patient-portal", component: HeaderComponent }]),
				{ provide: RoleService, useClass: MockPatientRoleService },
				{ provide: AuthenticationService, useValue: mockAuthenticationService },
				PatientService
			]
		}).compileComponents();

		fixture = TestBed.createComponent(HeaderComponent);
		component = fixture.componentInstance;
		router = TestBed.inject(Router);
		fixture.detectChanges();
	});

	/**
	 * Test to verify that navigation options for a medical professional are displayed correctly.
	 */
	it("should display the navigation options for a medical professional", () => {
		mockAuthenticationService.setMedicalProfessional(true);
		mockAuthenticationService.setPatient(false);
		fixture.detectChanges();

		const medicalProfessionalLinks = fixture.debugElement.nativeElement.querySelectorAll("a");

		expect(medicalProfessionalLinks.length).toBeGreaterThan(0);
		expect(medicalProfessionalLinks[0].textContent).toContain("Overview");
		expect(medicalProfessionalLinks[1].textContent).toContain("Patients");
	});

	/**
	 * Test to verify that navigation options for a patient are displayed correctly.
	 */
	it("should display the navigation options for a patient", () => {
		mockAuthenticationService.setMedicalProfessional(false);
		mockAuthenticationService.setPatient(true);
		fixture.detectChanges();

		const patientLinks = fixture.debugElement.nativeElement.querySelectorAll("a");

		expect(patientLinks.length).toBeGreaterThan(0);
		expect(patientLinks[0].textContent).toContain("Overview");
	});

	/**
	 * Test to verify that only an icon to the login page is displayed if the user is not logged in.
	 */
	it("should display only an icon to the login page if user is not logged in", () => {
		mockAuthenticationService.setMedicalProfessional(false);
		mockAuthenticationService.setPatient(false);
		fixture.detectChanges();

		const loggedoutLinks = fixture.debugElement.nativeElement.querySelectorAll("a");

		expect(loggedoutLinks.length).toBeGreaterThan(0);
		expect(loggedoutLinks[0].querySelector("mat-icon").classList).toContain("login-icon");
	});
});
