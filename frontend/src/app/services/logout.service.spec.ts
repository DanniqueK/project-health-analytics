import { TestBed } from "@angular/core/testing";

import { LogoutService } from "./logout.service";
import { provideHttpClient } from "@angular/common/http";

describe("LogoutService", () => {
	let service: LogoutService;

	beforeEach(() => {
		TestBed.configureTestingModule({ providers: [provideHttpClient()] });
		service = TestBed.inject(LogoutService);
	});

	it("should be created", () => {
		expect(service).toBeTruthy();
	});
});
