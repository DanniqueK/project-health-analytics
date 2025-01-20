import { TestBed } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { HealthDataService, HealthDataField } from "./health-data.service";
import { environment } from "../../../environments/environment";

describe("HealthDataService", () => {
	let service: HealthDataService;
	let httpMock: HttpTestingController;

	beforeEach(() => {
		TestBed.configureTestingModule({
			imports: [HttpClientTestingModule],
			providers: [HealthDataService]
		});
		service = TestBed.inject(HealthDataService);
		httpMock = TestBed.inject(HttpTestingController);
	});

	afterEach(() => {
		httpMock.verify();
	});

	it("should be created", () => {
		expect(service).toBeTruthy();
	});

	describe("getHealthDataByBsn", () => {
		it("should fetch and format health data", () => {
			const mockResponse = [
				{ dateOfCollection: "2023-01-01", field1: 10, field2: 20 },
				{ dateOfCollection: "2023-01-02", field1: 15, field2: 25 }
			];
			const formattedData = [
				{
					name: "Field1",
					series: [
						{ name: "1/1/2023", value: 10 },
						{ name: "1/2/2023", value: 15 }
					]
				},
				{
					name: "Field2",
					series: [
						{ name: "1/1/2023", value: 20 },
						{ name: "1/2/2023", value: 25 }
					]
				}
			];

			service.getHealthDataByBsn(123).subscribe(data => {
				expect(data).toEqual(formattedData);
			});

			const req = httpMock.expectOne(`${environment.API}/patient/health-data`);
			expect(req.request.method).toBe("POST");
			req.flush(mockResponse);
		});

		it("should handle error and return empty array", () => {
			service.getHealthDataByBsn(123).subscribe(data => {
				expect(data).toEqual([]);
			});

			const req = httpMock.expectOne(`${environment.API}/patient/health-data`);
			req.flush("Error", { status: 500, statusText: "Server Error" });
		});
	});

	describe("formatFieldName", () => {
		it("should format camelCase to space-separated words", () => {
			expect(service.formatFieldName("camelCaseField")).toBe("Camel Case Field");
		});
	});

	describe("formatHealthData", () => {
		it("should format health data for display", () => {
			const mockData = [
				{ dateOfCollection: "2023-01-01", field1: 10, field2: 20 },
				{ dateOfCollection: "2023-01-02", field1: 15, field2: 25 }
			];
			const formattedData = [
				{
					name: "Field1",
					series: [
						{ name: "1/1/2023", value: 10 },
						{ name: "1/2/2023", value: 15 }
					]
				},
				{
					name: "Field2",
					series: [
						{ name: "1/1/2023", value: 20 },
						{ name: "1/2/2023", value: 25 }
					]
				}
			];

			expect(service.formatHealthData(mockData)).toEqual(formattedData);
		});

		it("should return empty array if data is empty", () => {
			expect(service.formatHealthData([])).toEqual([]);
		});
	});

	describe("getDataByFieldName", () => {
		it("should retrieve data by field name", () => {
			const mockData = [
				{ dateOfCollection: "2023-01-01", field1: 10 },
				{ dateOfCollection: "2023-01-02", field1: 15 }
			];
			const fieldName = "Field1";
			const expectedData = {
				name: "Field1",
				series: [
					{ name: "1/1/2023", value: 10 },
					{ name: "1/2/2023", value: 15 }
				]
			};

			expect(service.getDataByFieldName(mockData, fieldName)).toEqual(expectedData);
		});
	});

	describe("setSelectedFieldData", () => {
		it("should set the selected health data field", () => {
			const fieldData: HealthDataField = {
				name: "Field1",
				series: [{ name: "1/1/2023", value: 10 }]
			};

			service.setSelectedFieldData(fieldData);
			service.selectedFieldData$.subscribe(data => {
				expect(data).toEqual(fieldData);
			});
		});

		it("should set the selected health data field to null", () => {
			service.setSelectedFieldData(null);
			service.selectedFieldData$.subscribe(data => {
				expect(data).toBeNull();
			});
		});
	});
});
