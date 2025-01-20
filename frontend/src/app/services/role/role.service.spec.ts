import { TestBed } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { RoleService } from "./role.service";
import { AuthenticationService } from "../authentication/authentication.service";
import { environment } from "../../../environments/environment";

describe("RoleService", () => {
	let service: RoleService;
	let httpMock: HttpTestingController;
	let authServiceSpy: jasmine.SpyObj<AuthenticationService>;

	beforeEach(() => {
		authServiceSpy = jasmine.createSpyObj("AuthenticationService", [
			"setPatientAuthenticated",
			"setMedicalProfessionalAuthenticated"
		]);

		TestBed.configureTestingModule({
			imports: [HttpClientTestingModule],
			providers: [{ provide: AuthenticationService, useValue: authServiceSpy }]
		});
		service = TestBed.inject(RoleService);
		httpMock = TestBed.inject(HttpTestingController);
	});

	afterEach(() => {
		httpMock.verify();
	});

	it("should be created", () => {
		expect(service).toBeTruthy();
	});

	it("should get user role and set authentication status", () => {
		const mockResponse = { userType: "patient" };

		service.getUserRole().subscribe(role => {
			expect(role).toBe("patient");
			expect(authServiceSpy.setPatientAuthenticated).toHaveBeenCalledWith(true);
		});

		const req = httpMock.expectOne(`${environment.API}/user/role`);
		expect(req.request.method).toBe("GET");
		req.flush(mockResponse);
	});

	it("should handle error when getting user role", () => {
		service.getUserRole().subscribe(role => {
			expect(role).toBe("none");
		});

		const req = httpMock.expectOne(`${environment.API}/user/role`);
		expect(req.request.method).toBe("GET");
		req.error(new ErrorEvent("Network error"));
	});
});
