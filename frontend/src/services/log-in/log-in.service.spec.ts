import { TestBed } from "@angular/core/testing";
import { LogInService } from "./log-in.service";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { environment } from "../../../environments/environment";
import { HttpErrorResponse } from "@angular/common/http";

describe("LogInService", () => {
	let service: LogInService;
	let httpMock: HttpTestingController;

	beforeEach(() => {
		TestBed.configureTestingModule({
			imports: [HttpClientTestingModule],
			providers: [LogInService]
		});
		service = TestBed.inject(LogInService);
		httpMock = TestBed.inject(HttpTestingController);
	});

	afterEach(() => {
		httpMock.verify();
	});

	it("should make POST request with correct params on checkLogin", () => {
		const mockCredentials = {
			bsnOrEmail: "test@example.com",
			password: "password123"
		};
		const mockResponse = { success: true };

		service.checkLogin(mockCredentials.bsnOrEmail, mockCredentials.password).subscribe(response => {
			expect(response).toEqual(mockResponse);
		});

		const req = httpMock.expectOne(`${environment.API}/user/login`);
		expect(req.request.method).toBe("POST");
		expect(req.request.body).toEqual(mockCredentials);
		expect(req.request.withCredentials).toBeTrue();
		expect(req.request.headers.get("Content-Type")).toBe("application/json");

		req.flush(mockResponse);
	});

	it("should handle error with custom error message", () => {
		const errorResponse = new HttpErrorResponse({
			error: { error: "Invalid credentials" },
			status: 401,
			statusText: "Unauthorized"
		});

		service.checkLogin("test@example.com", "wrong-password").subscribe(response => {
			expect(response.error).toBe("Invalid credentials");
		});

		const req = httpMock.expectOne(`${environment.API}/user/login`);
		req.flush(errorResponse.error, {
			status: errorResponse.status,
			statusText: errorResponse.statusText
		});
	});

	it("should handle error with default error message", () => {
		const errorResponse = new HttpErrorResponse({
			error: {},
			status: 500,
			statusText: "Internal Server Error"
		});

		service.checkLogin("test@example.com", "wrong-password").subscribe(response => {
			expect(response.error).toBe("An Unknown Error Occurred");
		});

		const req = httpMock.expectOne(`${environment.API}/user/login`);
		req.flush(errorResponse.error, {
			status: errorResponse.status,
			statusText: errorResponse.statusText
		});
	});
});
