import { TestBed } from "@angular/core/testing";

import { SaveNewPatientService } from "./save-new-patient.service";
import { HttpClient, HttpErrorResponse, HttpResponse, provideHttpClient } from "@angular/common/http";
import { of, throwError } from "rxjs";

/**
 * @todo, expand on this, these tests are really a rundamentary nothingburgers
 */
describe("SaveNewPatientService", () => {
	let httpClientSpy: jasmine.SpyObj<HttpClient>;
	let service: SaveNewPatientService;

	beforeEach(() => {
		TestBed.configureTestingModule({
			providers: [provideHttpClient()]
		});
		httpClientSpy = jasmine.createSpyObj("HttpClient", ["post"]);
		service = new SaveNewPatientService(httpClientSpy);
	});

	describe("[Method] newPatient", (): void => {
		it("should return a new password when valid data is provided", (done: DoneFn) => {
			const expectedPass: {
				pass: string;
			} = {
				pass: "RandomPassWhatever"
			};

			const successfulResponse = new HttpResponse<{
				pass: string;
			}>({
				body: expectedPass,
				status: 200
			});

			const validInput = {
				"firstName": "Maria",
				"middleName": "David",
				"lastName": "Wang",
				"dateOfBirth": "2024-11-21T11:04:51",
				"marrage": "single",
				"insurance": "De Christelijke Zorgverzekeraar",
				"street": "Damrak",
				"city": "Amsterdam",
				"province": "North-Holland",
				"country": "Netherlands",
				"number": "18",
				"postalCode": "1012 LH",
				"phone": "+31 06 14984325",
				"contactName": "Pedro da Silva",
				"contactPhone": "+31 06 29401938",
				"BSN": "928402948",
				"userName": "mariawang"
			};

			httpClientSpy.post.and.returnValue(of(successfulResponse));

			service.newPatient(validInput).subscribe({
				next: (res: HttpResponse<{ pass: string }>): void => {
					expect(res.body).withContext("incoming password").toEqual(successfulResponse.body);
					expect(res.status).withContext("incoming password").toEqual(successfulResponse.status);
					done();
				},
				error: done.fail
			});
			expect(httpClientSpy.post.calls.count()).withContext("one call").toBe(1);
		});

		it("should error upon sending an invalid input", (done: DoneFn) => {
			const invalidInput = {};

			const errorResponse: HttpErrorResponse = new HttpErrorResponse({
				error: "The input value 'firstName' is missing!",
				status: 400
			});

			httpClientSpy.post.and.returnValue(throwError(() => errorResponse));

			service.newPatient(invalidInput).subscribe({
				next: (): void => done.fail("Expected to fail, got results instead."),
				error: (error: HttpErrorResponse): void => {
					expect(error.error).toContain("The input value 'firstName' is missing!");
					expect(error.status).toBe(400);
					done();
				}
			});
		});
	});
});
