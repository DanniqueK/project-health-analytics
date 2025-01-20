import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { HealthDataListComponent } from "./health-data-list.component";
import { HealthDataService } from "../../../services/health-data/health-data.service";
import { PatientService } from "../../../services/patient/patient.service";
import { of, BehaviorSubject } from "rxjs";
import { CommonModule } from "@angular/common";

describe("HealthDataListComponent", () => {
	let component: HealthDataListComponent;
	let fixture: ComponentFixture<HealthDataListComponent>;
	let mockPatientService: jasmine.SpyObj<PatientService>;
	let mockHealthDataService: jasmine.SpyObj<HealthDataService>;
	let selectedPatientSubject: BehaviorSubject<any>;

	beforeEach(async () => {
		selectedPatientSubject = new BehaviorSubject(null);

		mockPatientService = jasmine.createSpyObj("PatientService", ["setSelectedPatient"]);
		Object.defineProperty(mockPatientService, "selectedPatient$", {
			get: () => selectedPatientSubject.asObservable()
		});

		mockHealthDataService = jasmine.createSpyObj("HealthDataService", ["getHealthDataByBsn", "setSelectedFieldData"]);
		mockHealthDataService.getHealthDataByBsn.and.returnValue(of([]));

		await TestBed.configureTestingModule({
			imports: [CommonModule, BrowserAnimationsModule, HealthDataListComponent],
			providers: [
				{ provide: PatientService, useValue: mockPatientService },
				{ provide: HealthDataService, useValue: mockHealthDataService }
			]
		}).compileComponents();

		fixture = TestBed.createComponent(HealthDataListComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});

	it("should load health data when patient is selected", () => {
		const mockPatient = { bsn: 123456789, name: "John Doe" };
		const mockHealthData = [
			{
				name: "Heart Rate",
				series: [{ name: "2024-11-22", value: 75 }]
			}
		];

		mockHealthDataService.getHealthDataByBsn.and.returnValue(of(mockHealthData));
		selectedPatientSubject.next(mockPatient);
		fixture.detectChanges();

		expect(mockHealthDataService.getHealthDataByBsn).toHaveBeenCalledWith(mockPatient.bsn);
		expect(component.healthDataFields.length).toBeGreaterThan(0);
	});

	it("should update patient name when patient is selected", () => {
		const mockPatient = { bsn: 123456789, name: "John Doe" };
		selectedPatientSubject.next(mockPatient);
		fixture.detectChanges();

		expect(component.patientName).toBe(mockPatient.name);
	});
});
