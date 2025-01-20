import { Component, Input } from "@angular/core";

@Component({
	selector: "app-info-text",
	standalone: true,
	imports: [],
	templateUrl: "./info-text.component.html",
	styleUrl: "./info-text.component.css"
})
export class InfoTextComponent {
	@Input() public head: string = "UNSET VALUE";
	@Input() public body: string = "UNSET VALUE";
}
