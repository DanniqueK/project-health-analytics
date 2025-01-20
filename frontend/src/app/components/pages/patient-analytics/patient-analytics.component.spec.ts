import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { PatientAnalyticsComponent } from "./patient-analytics.component";
import { HealthDataService } from "../../../services/health-data/health-data.service";
import { PatientService, SelectedPatient } from "../../../services/patient/patient.service";
import { of, BehaviorSubject } from "rxjs";
import { Router } from "@angular/router";
import { provideHttpClient } from "@angular/common/http";
import { HealthDataListComponent } from "../../elements/health-data-list/health-data-list.component";
import { PatientGraphsComponent } from "../../elements/patient-graphs/patient-graphs.component";
import { environment } from "../../../../environments/environment";

describe("PatientAnalyticsComponent", () => {
	let component: PatientAnalyticsComponent;
	let fixture: ComponentFixture<PatientAnalyticsComponent>;
	let mockPatientService: jasmine.SpyObj<PatientService>;
	let mockHealthDataService: jasmine.SpyObj<HealthDataService>;
	let mockRouter: jasmine.SpyObj<Router>;
	let selectedPatientSubject: BehaviorSubject<SelectedPatient | null>;
	let selectedFieldDataSubject: BehaviorSubject<any>;

	beforeEach(async () => {
		selectedPatientSubject = new BehaviorSubject<SelectedPatient | null>(null);
		selectedFieldDataSubject = new BehaviorSubject<any>(null);

		mockPatientService = jasmine.createSpyObj("PatientService", ["setSelectedPatient"]);
		Object.defineProperty(mockPatientService, "selectedPatient$", {
			get: () => selectedPatientSubject.asObservable()
		});

		mockHealthDataService = jasmine.createSpyObj("HealthDataService", ["getHealthDataByBsn", "setSelectedFieldData"]);
		Object.defineProperty(mockHealthDataService, "selectedFieldData$", {
			get: () => selectedFieldDataSubject.asObservable()
		});
		mockHealthDataService.getHealthDataByBsn.and.returnValue(of([]));

		mockRouter = jasmine.createSpyObj("Router", ["navigate"]);

		await TestBed.configureTestingModule({
			imports: [PatientAnalyticsComponent, BrowserAnimationsModule, HealthDataListComponent, PatientGraphsComponent],
			providers: [
				{ provide: PatientService, useValue: mockPatientService },
				{ provide: HealthDataService, useValue: mockHealthDataService },
				{ provide: Router, useValue: mockRouter },
				provideHttpClient()
			]
		}).compileComponents();

		fixture = TestBed.createComponent(PatientAnalyticsComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});

	it("should display an error message if no patient is selected", () => {
		selectedPatientSubject.next(null);
		fixture.detectChanges();

		expect(component.errorMessage).toBe("No patient selected. Please select a patient from the patient list.");
	});

	it("should display the selected patient", () => {
		const selectedPatient: SelectedPatient = { bsn: 123456789, name: "John Doe" };
		selectedPatientSubject.next(selectedPatient);
		fixture.detectChanges();

		expect(component.selectedPatient).toEqual(selectedPatient);
		expect(component.errorMessage).toBe("");
	});

	it("should update selected field data", () => {
		const fieldData = {
			name: "Heart Rate",
			series: [{ name: "2024-11-22", value: 75 }]
		};

		component.onFieldSelected(fieldData);
		fixture.detectChanges();

		expect(component.selectedFieldData).toEqual(fieldData);
	});

	it("should set error message when patient BSN is 0", () => {
		const invalidPatient: SelectedPatient = { bsn: 0, name: "Invalid Patient" };
		selectedPatientSubject.next(invalidPatient);
		fixture.detectChanges();

		expect(component.errorMessage).toBe(environment.MESSAGES.NO_PATIENT_SELECTED_ERROR);
	});

	it("should initialize with null selectedFieldData", () => {
		expect(component.selectedFieldData).toBeUndefined();
	});

	it("should handle null field data selection", () => {
		component.onFieldSelected(null);
		fixture.detectChanges();

		expect(component.selectedFieldData).toBeNull();
	});

	it("should handle complex field data selection", () => {
		const complexFieldData = {
			name: "Blood Pressure",
			series: [
				{ name: "2024-01-01", value: "120/80" },
				{ name: "2024-01-02", value: "118/79" }
			],
			metadata: {
				unit: "mmHg",
				type: "measurement"
			}
		};

		component.onFieldSelected(complexFieldData);
		fixture.detectChanges();

		expect(component.selectedFieldData).toEqual(complexFieldData);
	});

	it("should maintain selected field data after patient update", () => {
		const fieldData = { name: "Weight", series: [{ name: "2024-01-01", value: 70 }] };
		component.onFieldSelected(fieldData);

		const newPatient: SelectedPatient = { bsn: 987654321, name: "Jane Doe" };
		selectedPatientSubject.next(newPatient);
		fixture.detectChanges();

		expect(component.selectedFieldData).toEqual(fieldData);
	});
});
