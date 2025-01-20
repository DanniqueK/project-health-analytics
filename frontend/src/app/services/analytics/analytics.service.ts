import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Entry } from "../../interfaces/entry";

@Injectable({
	providedIn: "root"
})
/**
 *
 * Class for the analytics service. This class should send a HTTP GET and POST request to the server, but for
 * now it will make use of mock data since the backend hasn't been fully implemented yet.
 */
export class AnalyticsService {
	// Mock entries
	private entries: Entry[] = [
		{
			date: "17-09-2024",
			summary: "Felt tired and had a headache. Ate mostly processed food.",
			prediction: "Fatigue likely due to poor diet and dehydration.",
			recommendation: "Drink more water and eat fresh fruits and vegetables."
		},
		{
			date: "23-09-2024",
			summary: "Energy improved, but back pain persists after sitting.",
			prediction: "Back pain may worsen without posture changes.",
			recommendation: "Stretch daily and take regular breaks from sitting."
		},
		{
			date: "06-10-2024",
			summary: "Caught a cold; sleep has been poor (5–6 hours nightly).",
			prediction: "Recovery may slow without better sleep.",
			recommendation: "Sleep 7–8 hours and drink warm teas to soothe symptoms."
		},
		{
			date: "13-10-2024",
			summary: "Cold improved, but work stress caused headaches.",
			prediction: "Stress could lead to recurring headaches.",
			recommendation: "Practice mindfulness and set work boundaries."
		},
		{
			date: "29-10-2024",
			summary: "Health steady; occasional back pain remains.",
			prediction: "Back pain should improve with continued care.",
			recommendation: "Keep stretching and consider a massage for relief."
		}
	];

	/**
	 * Loads in all the patient's entries. It should receive the entries stored in the
	 * database, but for now it will use mock entries.
	 *
	 * @returns a list of entries to the module.
	 */
	public loadEntries(): Entry[] {
		return this.entries;
	}

	/**
	 * For now it will generate a mock entry because this is a front-end issue and no
	 * connection with the database is needed yet.
	 * @returns the list with an added entry.
	 */
	public generateNewEntry(): Entry[] {
		try {
			const date: string = new Date().toISOString().slice(0, 10);
			const reversedDate: string = date.split("-").reverse().join("-");
			const newEntry: Entry = {
				date: reversedDate,
				summary: "This is a new summary.",
				prediction: "This is a new prediction.",
				recommendation: "This is a new recommendation."
			};

			// Adds the newly generated entry to the list.
			return this.entries.concat(newEntry);
		} catch (e: any) {
			// Return error or throw it further.
			throw new Error(`Failed to generate a new entry: ${e.message || e}`);
		}
	}
}
