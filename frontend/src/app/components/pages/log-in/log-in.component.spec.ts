import { ComponentFixture, TestBed } from "@angular/core/testing";
import { HttpClientModule } from "@angular/common/http"; // Import HttpClientModule
import { FormsModule } from "@angular/forms"; // Import FormsModule
import { LogInComponent } from "./log-in.component";
import { LogInService } from "../../../services/log-in/log-in.service"; // Import your service

describe("LogInComponent", () => {
	let component: LogInComponent;
	let fixture: ComponentFixture<LogInComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [HttpClientModule, FormsModule], // Add HttpClientModule and FormsModule to imports
			providers: [LogInService] // Provide the service
		}).compileComponents();

		fixture = TestBed.createComponent(LogInComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});

	it("should return a error if input is empty", () => {
		component.bsnOrEmail = "";
		component.password = "";
		component.checkUserInput();
		expect(component.errorMessage).toBe("Please fill out all the fields");
	});

	it("should return a error if input is invalid", () => {
		component.bsnOrEmail = "test";
		component.password = "test";
		component.checkUserInput();
		expect(component.errorMessage).toBe("Invalid email or bsn!");
	});
});
