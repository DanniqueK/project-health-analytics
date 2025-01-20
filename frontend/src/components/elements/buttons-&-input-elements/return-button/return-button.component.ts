import { Component } from "@angular/core";
import { ButtonComponent } from "../button/button.component";
import { FontAwesomeModule, IconDefinition } from "@fortawesome/angular-fontawesome";
import { faLeftLong } from "@fortawesome/free-solid-svg-icons";
import { Location } from "@angular/common";

@Component({
	selector: "app-return-button",
	standalone: true,
	imports: [ButtonComponent, FontAwesomeModule],
	templateUrl: "./return-button.component.html",
	styleUrl: "./return-button.component.css"
})
export class ReturnButtonComponent {
	protected returnIcon: IconDefinition = faLeftLong;
	constructor(protected location: Location) {}
}
