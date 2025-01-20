import { TestBed } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { PatientService, SelectedPatient } from "./patient.service";
import { environment } from "../../../environments/environment";
import { take } from "rxjs/operators";

describe("PatientService", () => {
	let service: PatientService;
	let httpMock: HttpTestingController;

	beforeEach(() => {
		TestBed.configureTestingModule({
			imports: [HttpClientTestingModule],
			providers: [PatientService]
		});
		service = TestBed.inject(PatientService);
		httpMock = TestBed.inject(HttpTestingController);
	});

	afterEach(() => {
		httpMock.verify();
	});

	describe("setSelectedPatient", () => {
		it("should set and emit selected patient", done => {
			const mockPatient: SelectedPatient = { bsn: 123, name: "John Doe" };

			service.setSelectedPatient(mockPatient);

			service.selectedPatient$.pipe(take(1)).subscribe({
				next: patient => {
					expect(patient).toBeDefined();
					expect(patient?.bsn).toBe(mockPatient.bsn);
					expect(patient?.name).toBe(mockPatient.name);
					done();
				}
			});
		});

		it("should handle null patient", done => {
			service.setSelectedPatient(null);

			service.selectedPatient$.pipe(take(1)).subscribe({
				next: patient => {
					expect(patient).toBeNull();
					done();
				}
			});
		});
	});

	describe("searchPatient", () => {
		it("should make POST request with search query", () => {
			const searchQuery = "John";
			const mockResponse = [{ id: 1, name: "John" }];

			service.searchPatient(searchQuery).subscribe(response => {
				expect(response).toEqual(mockResponse);
			});

			const req = httpMock.expectOne(`${environment.API}/patient-list`);
			expect(req.request.method).toBe("POST");
			expect(req.request.body).toEqual({ searchQuery });
			expect(req.request.withCredentials).toBeTrue();
			req.flush(mockResponse);
		});
	});

	describe("updateDataSource", () => {
		it("should handle uppercase field names", () => {
			const input = [
				{
					Name: "John Doe",
					Bsn: 123,
					DateOfBirth: "2023-01-01T00:00:00"
				}
			];

			const expected = [
				{
					name: "John Doe",
					bsn: 123,
					birth: "2023-01-01",
					view: "View"
				}
			];

			expect(service.updateDataSource(input)).toEqual(expected);
		});

		it("should handle lowercase field names", () => {
			const input = [
				{
					name: "John Doe",
					bsn: 123,
					dateOfBirth: "2023-01-01T00:00:00"
				}
			];

			const expected = [
				{
					name: "John Doe",
					bsn: 123,
					birth: "2023-01-01",
					view: "View"
				}
			];

			expect(service.updateDataSource(input)).toEqual(expected);
		});

		it("should handle missing fields", () => {
			const input = [
				{
					Name: "John Doe",
					dateOfBirth: "2023-01-01T00:00:00"
				}
			];

			const expected = [
				{
					name: "John Doe",
					bsn: undefined,
					birth: "2023-01-01",
					view: "View"
				}
			];

			expect(service.updateDataSource(input)).toEqual(expected);
		});

		it("should handle empty input array", () => {
			expect(service.updateDataSource([])).toEqual([]);
		});
	});
});
