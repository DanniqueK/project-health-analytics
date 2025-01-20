import { Component } from "@angular/core";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";

@Component({
	selector: "app-toggle-button",
	standalone: true,
	imports: [FontAwesomeModule],
	templateUrl: "./toggle-button.component.html",
	styleUrl: "./toggle-button.component.css"
})
export class ToggleButtonComponent {
	faCheck = faCheck;
}
