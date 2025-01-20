import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RouterTestingModule } from "@angular/router/testing";

import { PatientListComponent } from "./patient-list.component";
import { HttpClientModule } from "@angular/common/http";
import { PatientService } from "../../../services/patient/patient.service";

describe("PatientListComponent", () => {
	let component: PatientListComponent;
	let fixture: ComponentFixture<PatientListComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [
				PatientListComponent,
				BrowserAnimationsModule, // For animations
				RouterTestingModule, // For ActivatedRoute
				HttpClientModule // For HttpClient
			],
			providers: [
				// Add your providers here
				PatientService
			]
		}).compileComponents();

		fixture = TestBed.createComponent(PatientListComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});
});
