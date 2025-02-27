import { ComponentFixture, TestBed } from "@angular/core/testing";

import { PatientRegistrationComponent } from "./patient-registration.component";
import { provideHttpClient } from "@angular/common/http";

describe("PatientRegistrationComponent", () => {
	let component: PatientRegistrationComponent;
	let fixture: ComponentFixture<PatientRegistrationComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			providers: [provideHttpClient()],
			imports: [PatientRegistrationComponent]
		}).compileComponents();

		fixture = TestBed.createComponent(PatientRegistrationComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});
});
