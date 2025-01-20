import { Component } from "@angular/core";
import { ToggleButtonComponent } from "../../elements/buttons-&-input-elements/toggle-button/toggle-button.component";
import { InputComponent } from "../../elements/buttons-&-input-elements/input/input.component";

@Component({
	selector: "app-style-reference",
	standalone: true,
	imports: [ToggleButtonComponent, InputComponent],
	templateUrl: "./style-reference.component.html",
	styleUrl: "./style-reference.component.css"
})
export class StyleReferenceComponent {}
