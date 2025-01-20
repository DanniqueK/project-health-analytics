import { Component, Input } from "@angular/core";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faCube, IconDefinition } from "@fortawesome/free-solid-svg-icons";

@Component({
	selector: "app-formgroup-box",
	standalone: true,
	imports: [FontAwesomeModule],
	templateUrl: "./formgroup-box.component.html",
	styleUrl: "./formgroup-box.component.css"
})
export class FormgroupBoxComponent {
	@Input() public icon: IconDefinition = faCube;
	@Input() public head: string = "UNSET VALUE";
}
