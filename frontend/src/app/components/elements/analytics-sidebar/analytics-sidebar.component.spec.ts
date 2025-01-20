import { ComponentFixture, TestBed } from "@angular/core/testing";
import { AnalyticsSidebarComponent } from "./analytics-sidebar.component";
import { AnalyticsService } from "../../../services/analytics/analytics.service";
import { Entry } from "../../../interfaces/entry";
import { of } from "rxjs";
import { By } from "@angular/platform-browser";

const entries: Entry[] = [];

class MockAnalyticsService {
	loadEntries(): Entry[] {
		return entries;
	}

	generateNewEntry(): Entry[] {
		return [];
	}
}

// Arrange
describe("AnalyticsSidebarComponent", () => {
	let component: AnalyticsSidebarComponent;
	let fixture: ComponentFixture<AnalyticsSidebarComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [AnalyticsSidebarComponent],
			providers: [{ provide: AnalyticsService, useClass: MockAnalyticsService }]
		}).compileComponents();

		fixture = TestBed.createComponent(AnalyticsSidebarComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	// Test that checks if an error message is displayed if no entries could be found

	it("should display an error message if no entries are found", () => {
		// Arrange
		component.entries = component.loadEntries();
		fixture.detectChanges();

		// Act
		component.noEntriesFound = component.entries.length === 0;
		fixture.detectChanges();

		// Assert
		expect(component.noEntriesFound).toBeTrue();

		const compiled = fixture.nativeElement as HTMLElement;
		const errorMessage = compiled.querySelector("#noEntriesFound");

		expect(errorMessage).toBeTruthy();
		expect(errorMessage?.textContent).toContain("No entries found. Please generate a new entry");
	});

	it("should display an error message if no entry could be generated.", () => {
		spyOn(component, "generateEntry").and.callFake(() => {
			component.entryCreationFailed = true;
		});

		const button = fixture.debugElement.query(By.css(".analytics-sidebar-button"));
		button.triggerEventHandler("click", null);

		// Detect changes to update the DOM
		fixture.detectChanges();

		// Check if the error message is displayed
		const errorMessage = fixture.debugElement.query(By.css("#entryCreationFailed"));
		expect(errorMessage).toBeTruthy(); // Ensure the error message element exists
		expect(errorMessage.nativeElement.textContent).toContain("Failed to generate an entry.");
	});
});
