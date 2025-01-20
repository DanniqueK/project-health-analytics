import { NgFor, CommonModule } from "@angular/common";
import { Component, ElementRef, ViewChild } from "@angular/core";
import { AnalyticsService } from "../../../services/analytics/analytics.service";
import { Entry } from "../../../interfaces/entry";

@Component({
	selector: "app-analytics-sidebar",
	standalone: true,
	imports: [NgFor, CommonModule],
	templateUrl: "./analytics-sidebar.component.html",
	styleUrl: "./analytics-sidebar.component.css"
})

/**
 * Class for the Analytics Sidebar. The sidebar should be used by the medical professional only.
 * it will show entries with a summary, prediction and recommendation. These are all based on the
 * patient's health.
 *
 * @Author Latricha Seym
 */
export class AnalyticsSidebarComponent {
	public entries: Entry[] | undefined;
	public noEntriesFound: boolean = false;
	public entryCreationFailed: boolean = false;

	public errorMessage: string = "";

	constructor(private analyticsService: AnalyticsService) {}

	public ngOnInit() {
		this.entries = this.loadEntries();
	}

	/**
	 * Adds an is-open class to the entry when the user has clicked
	 * on the entry's header.
	 * @param event
	 *
	 */
	public toggleDetails(event: Event): void {
		const entryHeader: HTMLElement = event.currentTarget as HTMLElement;
		const entry: HTMLElement = entryHeader.parentElement as HTMLElement;

		if (entry.classList.contains("is-open")) {
			entry.classList.remove("is-open");
		} else {
			entry.classList.add("is-open");
		}
	}

	/**
	 *
	 * Loads mock entries into the page.
	 *
	 * @returns An array of mock entries.
	 */
	public loadEntries(): Entry[] {
		const entries: Entry[] = this.analyticsService.loadEntries();

		// Check if entries are empty
		if (entries.length === 0) {
			this.noEntriesFound = true;
		}

		return entries;
	}

	/**
	 * Generates a new entry for the patient based on the health data.
	 *
	 */
	public generateEntry(): void {
		this.entries = this.analyticsService.generateNewEntry();
	}
}
