import { Component } from "@angular/core";
import { DropdownComponent } from "../../../../elements/buttons-&-input-elements/dropdown/dropdown.component";
import { InputComponent } from "../../../../elements/buttons-&-input-elements/input/input.component";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { FormgroupBoxComponent } from "../../../../elements/formgroup-box/formgroup-box.component";
import { InfoTextComponent } from "../../../../elements/info-text/info-text.component";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faPersonHalfDress, IconDefinition } from "@fortawesome/free-solid-svg-icons";

@Component({
	selector: "app-personal-form",
	standalone: true,
	imports: [
		ReactiveFormsModule,
		FormsModule,
		FontAwesomeModule,
		FormgroupBoxComponent,
		InfoTextComponent,
		InputComponent,
		DropdownComponent
	],
	templateUrl: "./personal-form.component.html",
	styleUrl: "./personal-form.component.css"
})
export class PersonalFormComponent {
	protected personIcon: IconDefinition = faPersonHalfDress;
	protected maritalStatus: string[] = ["single", "cohabitation", "married", "divorced", "widowed"]; //hardcoded options for now
}
