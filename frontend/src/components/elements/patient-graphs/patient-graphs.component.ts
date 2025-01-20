import { Component, OnDestroy, OnInit } from "@angular/core";
import { NgxChartsModule, Color, ScaleType } from "@swimlane/ngx-charts";
import { CurveFactory, curveLinear } from "d3-shape";
import { HealthDataService, HealthDataField } from "../../../services/health-data/health-data.service";
import { Subscription } from "rxjs";
import { environment } from "../../../../environments/environment";

@Component({
	selector: "app-patient-graphs",
	standalone: true,
	imports: [NgxChartsModule],
	templateUrl: "./patient-graphs.component.html",
	styleUrls: ["./patient-graphs.component.css"]
})
export class PatientGraphsComponent implements OnInit, OnDestroy {
	private subscription: Subscription = new Subscription();

	// The data for the graph and table
	protected data: HealthDataField[] = [];
	protected originalData: HealthDataField[] = [];

	// What to visualize; graph or Table; default graph.
	protected currentView: string = "graph";

	// What data to show, latest or oldest; default latest.
	protected order: string = "latest";

	// Text to display
	protected title: string = "";
	protected errorMessage: string = "";

	// Boolean for if the data is numeric, for choosing a graph or table.
	protected isNumericData: boolean = true;

	// Values for the graph
	protected minValue: number = 0;
	protected maxValue: number = 0;

	// Values for the table
	protected tableLength: number = 8;
	protected tableHeight: number = 400;

	// for the table if it's scrollable.
	protected showScrollIndicator: boolean = false;

	/** The curve factory for the graph */
	curve: CurveFactory = curveLinear;
	colorScheme: Color = {
		name: "customScheme",
		selectable: true,
		group: ScaleType.Ordinal,
		domain: ["#6ebbcf"]
	};

	constructor(private healthDataService: HealthDataService) {}

	/**
	 * Initializes the component and subscribes to the health data service
	 */
	ngOnInit(): void {
		this.subscription = this.healthDataService.selectedFieldData$.subscribe(fieldData => {
			if (fieldData) {
				this.title = fieldData.name;
				this.originalData = [fieldData];
				this.sortDataByDate();
				this.isNumericData = this.checkIfNumeric(fieldData.series);
				if (this.isNumericData) {
					this.adjustMinMaxValues(fieldData.series);
				} else {
					this.currentView = "table"; // Switch to table view if no numeric data
				}
				this.updateFilteredData();
			} else {
				this.errorMessage = environment.MESSAGES.NO_HEALTH_DATA_FOUND;
			}
		});
	}

	/**
	 * Unsubscribes from the health data service when the component is destroyed
	 */
	ngOnDestroy(): void {
		if (this.subscription) {
			this.subscription.unsubscribe();
		}
	}

	/**
	 * Sets the current view (graph or table)
	 * @param view The view to set
	 */
	protected setView(view: string): void {
		this.currentView = view;
	}

	/**
	 * Sets the number of entries to display in the table
	 * @param event The event containing the selected number of entries
	 */
	setTableLength(event: Event): void {
		const target = event.target as HTMLSelectElement;
		const length = target.value === "all" ? this.originalData[0]?.series.length : parseInt(target.value, 10);
		this.tableLength = length || 8; // Default to 8 if length is null or undefined
		this.updateFilteredData();
	}

	/**
	 * Toggles the order of the data (latest or oldest)
	 */
	toggleOrder(): void {
		this.order = this.order === "latest" ? "oldest" : "latest";
		this.sortDataByDate();
		this.updateFilteredData();
	}

	/**
	 * Adjusts the minimum and maximum values for the y-axis
	 * @param series The data series
	 */
	private adjustMinMaxValues(series: { name: string; value: any }[]): void {
		const values = series.map(item => Number(item.value));
		this.minValue = Math.min(...values) * 0.9;
		this.maxValue = Math.max(...values) * 1.1;
	}

	/**
	 * Checks if the data series is numeric
	 * @param series The data series
	 * @returns Whether the data series is numeric
	 */
	private checkIfNumeric(series: { name: string; value: any }[]): boolean {
		return series.every(item => !isNaN(Number(item.value)));
	}

	/**
	 * Sorts the data by date
	 */
	private sortDataByDate(): void {
		this.originalData[0].series.sort((a, b) => {
			const dateA = new Date(a.name).getTime();
			const dateB = new Date(b.name).getTime();
			return this.order === "latest" ? dateB - dateA : dateA - dateB;
		});
	}

	/**
	 * Updates the filtered data based on the selected number of entries and order
	 */
	private updateFilteredData(): void {
		// First, always sort in chronological order (ascending)
		this.originalData[0].series.sort((a, b) => {
			const dateA = new Date(a.name).getTime();
			const dateB = new Date(b.name).getTime();
			return dateA - dateB; // Always ascending
		});

		if (this.tableLength === this.originalData[0]?.series.length || this.tableLength === 0) {
			// Show all entries
			this.data = [
				{
					...this.originalData[0],
					series: [...this.originalData[0].series]
				}
			];
		} else {
			// Get the correct slice based on order
			const series = [...this.originalData[0].series];
			if (this.order === "latest") {
				// For latest, take the last n entries
				this.data = [
					{
						...this.originalData[0],
						series: series.slice(-this.tableLength)
					}
				];
			} else {
				// For oldest, take the first n entries
				this.data = [
					{
						...this.originalData[0],
						series: series.slice(0, this.tableLength)
					}
				];
			}
		}

		// Show scroll indicator if the filtered data length is less than the original data length
		this.showScrollIndicator = this.data[0]?.series.length < this.originalData[0]?.series.length;
	}
}
