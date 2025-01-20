import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { PatientService, SelectedPatient } from "../../../services/patient/patient.service";
import { of, throwError } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";
import { environment } from "../../../../environments/environment";
import { PatientRecordsComponent } from "./patient-records.component";
import { HttpClientModule } from "@angular/common/http";
import { SimpleChange } from "@angular/core";
import { MatTableDataSource } from "@angular/material/table";

describe("PatientRecordsComponent", () => {
	let component: PatientRecordsComponent;
	let fixture: ComponentFixture<PatientRecordsComponent>;
	let patientServiceSpy: jasmine.SpyObj<PatientService>;
	let routerSpy: jasmine.SpyObj<Router>;

	beforeEach(async () => {
		// Add updateDataSource to the spy methods
		patientServiceSpy = jasmine.createSpyObj("PatientService", [
			"getPatients",
			"searchPatient",
			"setSelectedPatient",
			"updateDataSource"
		]);
		routerSpy = jasmine.createSpyObj("Router", ["navigate"]);

		// Set up the default return values
		patientServiceSpy.getPatients.and.returnValue(of([]));
		patientServiceSpy.searchPatient.and.returnValue(of([]));
		patientServiceSpy.updateDataSource.and.callFake(data => data); // Return input data by default

		await TestBed.configureTestingModule({
			imports: [PatientRecordsComponent, BrowserAnimationsModule, HttpClientModule],
			providers: [
				{ provide: PatientService, useValue: patientServiceSpy },
				{ provide: Router, useValue: routerSpy },
				{ provide: ActivatedRoute, useValue: { params: of({}) } }
			]
		}).compileComponents();

		fixture = TestBed.createComponent(PatientRecordsComponent);
		component = fixture.componentInstance;
		component["dataSource"] = new MatTableDataSource<any>([]);
		fixture.detectChanges();
	});

	describe("fetchAllPatients", () => {
		const mockData = [
			{ bsn: 123, name: "John Doe", birth: "1990-01-01" },
			{ bsn: 456, name: "Jane Doe", birth: "1991-01-01" }
		];

		beforeEach(() => {
			component["dataSource"] = new MatTableDataSource<any>([]);
			patientServiceSpy.updateDataSource.and.returnValue(mockData);
		});

		it("should fetch and display patients on init", () => {
			patientServiceSpy.getPatients.and.returnValue(of(mockData));
			component.ngOnInit();
			expect(component["dataSource"].data).toEqual(mockData);
		});
		it("should handle empty patient list", () => {
			patientServiceSpy.getPatients.and.returnValue(of([]));
			component.ngOnInit();
			expect(component["dataSource"].data[0].name).toBe(environment.MESSAGES.NO_ASSIGNED_PATIENTS);
		});

		it("should handle error when fetching patients", () => {
			patientServiceSpy.getPatients.and.returnValue(throwError(() => new Error("Error")));
			component.ngOnInit();
			expect(component["dataSource"].data[0].name).toBe(environment.MESSAGES.ERROR_FETCH_PATIENT_MESSAGE);
		});
	});

	describe("searchPatients", () => {
		beforeEach(() => {
			component["dataSource"] = new MatTableDataSource<any>([]);
		});

		it("should search patients when input changes", () => {
			const searchTerm = "John";
			const mockSearchResult = [{ bsn: 123, name: "John Doe", birth: "1990-01-01" }];
			patientServiceSpy.searchPatient.and.returnValue(of(mockSearchResult));
			patientServiceSpy.updateDataSource.and.returnValue(mockSearchResult);

			component.searchPatients(searchTerm);

			expect(patientServiceSpy.searchPatient).toHaveBeenCalledWith(searchTerm);
			expect(component["dataSource"].data).toEqual(mockSearchResult);
		});

		it("should handle no search results", () => {
			patientServiceSpy.searchPatient.and.returnValue(of([]));
			component.searchPatients("NonExistent");
			expect(component["dataSource"].data[0].name).toBe(environment.MESSAGES.NO_PATIENT_FOUND);
		});

		it("should handle search error", () => {
			patientServiceSpy.searchPatient.and.returnValue(throwError(() => new Error("Error")));
			component.searchPatients("Test");
			expect(component["dataSource"].data[0].name).toBe(environment.MESSAGES.ERROR_FETCH_PATIENT_MESSAGE);
		});
	});

	describe("selectPatient", () => {
		beforeEach(() => {
			component["dataSource"] = new MatTableDataSource<any>([]);
		});

		it("should set selected patient and navigate", () => {
			const bsn = 123;
			const name = "John Doe";

			component.selectPatient(bsn, name);

			expect(patientServiceSpy.setSelectedPatient).toHaveBeenCalledWith({ bsn, name });
			expect(routerSpy.navigate).toHaveBeenCalledWith(["/patient-portal/patient-analytics"]);
		});
	});

	describe("setDataSource", () => {
		beforeEach(() => {
			component["dataSource"] = new MatTableDataSource<any>([]);
		});

		it("should set error data source correctly", () => {
			const errorMessage = "Test Error";
			component["setDataSource"](errorMessage, true);

			expect(component["dataSource"].data[0]).toEqual({
				name: errorMessage,
				birth: "",
				view: "",
				isError: true
			});
		});

		it("should set normal data source correctly", () => {
			const normalData = [{ name: "John", birth: "1990-01-01" }];
			component["setDataSource"](normalData, false);
			expect(component["dataSource"].data).toEqual(normalData);
		});
	});
});
