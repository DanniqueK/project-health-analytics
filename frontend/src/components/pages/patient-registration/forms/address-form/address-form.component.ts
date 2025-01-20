import { Component } from "@angular/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { InputComponent } from "../../../../elements/buttons-&-input-elements/input/input.component";
import { FormgroupBoxComponent } from "../../../../elements/formgroup-box/formgroup-box.component";
import { InfoTextComponent } from "../../../../elements/info-text/info-text.component";
import { faHouseChimneyUser, IconDefinition } from "@fortawesome/free-solid-svg-icons";

@Component({
	selector: "app-address-form",
	standalone: true,
	imports: [
		ReactiveFormsModule,
		FormsModule,
		FontAwesomeModule,
		FormgroupBoxComponent,
		InfoTextComponent,
		InputComponent
	],
	templateUrl: "./address-form.component.html",
	styleUrl: "./address-form.component.css"
})
export class AddressFormComponent {
	protected addressIcon: IconDefinition = faHouseChimneyUser;
	// This is the laziest regex I've ever written, and differs from the final validation.
	protected postalcode: RegExp = /^(\d)?(\d)?(\d)?(\d)?(\s)?(\D)?(\D)?$/;
}
