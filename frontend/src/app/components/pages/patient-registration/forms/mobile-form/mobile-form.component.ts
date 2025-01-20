import { Component } from "@angular/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { InputComponent } from "../../../../elements/buttons-&-input-elements/input/input.component";
import { FormgroupBoxComponent } from "../../../../elements/formgroup-box/formgroup-box.component";
import { faSquarePhone, IconDefinition } from "@fortawesome/free-solid-svg-icons";

@Component({
	selector: "app-mobile-form",
	standalone: true,
	imports: [ReactiveFormsModule, FormsModule, FontAwesomeModule, FormgroupBoxComponent, InputComponent],
	templateUrl: "./mobile-form.component.html",
	styleUrl: "./mobile-form.component.css"
})
export class MobileFormComponent {
	protected phoneIcon: IconDefinition = faSquarePhone;
	protected phone: RegExp = /^\+([\d\s]+)?$/; // I can make the phone component its own thing
}
