import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { SearchBarComponent } from "./search-bar.component";

describe("SearchBarComponent", () => {
	let component: SearchBarComponent;
	let fixture: ComponentFixture<SearchBarComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [
				SearchBarComponent,
				BrowserAnimationsModule // Add BrowserAnimationsModule here
			]
		}).compileComponents();

		fixture = TestBed.createComponent(SearchBarComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});

	it("should check if the input is empty", () => {
		component.searchedPatient = "";
		component.searchPatient();
		expect(component.errorMessage).toBe("Please enter a patient name to search for");
	});
});
