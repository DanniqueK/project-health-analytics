import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { PatientGraphsComponent } from "./patient-graphs.component";
import { HealthDataService } from "../../../services/health-data/health-data.service";
import { BehaviorSubject } from "rxjs";

describe("PatientGraphsComponent", () => {
	let component: PatientGraphsComponent;
	let fixture: ComponentFixture<PatientGraphsComponent>;
	let mockHealthDataService: jasmine.SpyObj<HealthDataService>;
	let selectedFieldDataSubject: BehaviorSubject<any>;

	beforeEach(async () => {
		selectedFieldDataSubject = new BehaviorSubject<any>(null);

		mockHealthDataService = jasmine.createSpyObj("HealthDataService", ["setSelectedFieldData"]);
		Object.defineProperty(mockHealthDataService, "selectedFieldData$", {
			get: () => selectedFieldDataSubject.asObservable()
		});

		await TestBed.configureTestingModule({
			imports: [PatientGraphsComponent, BrowserAnimationsModule],
			providers: [{ provide: HealthDataService, useValue: mockHealthDataService }]
		}).compileComponents();

		fixture = TestBed.createComponent(PatientGraphsComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});

	it("should load and display health data when selected", () => {
		const fieldData = {
			name: "Heart Rate",
			series: [{ name: "2024-11-22", value: 75 }]
		};

		selectedFieldDataSubject.next(fieldData);
		fixture.detectChanges();

		// Use type assertion to access protected properties
		expect((component as any).data).toEqual([fieldData]);
		expect((component as any).title).toBe("Heart Rate");
		expect((component as any).isNumericData).toBeTrue();
	});
});
