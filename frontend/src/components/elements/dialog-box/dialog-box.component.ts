import { NgIf } from "@angular/common";
import { Component, inject } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import {
	MAT_DIALOG_DATA,
	MatDialogActions,
	MatDialogContent,
	MatDialogRef,
	MatDialogTitle
} from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";

// # Source: https://material.angular.io/components/dialog/overview
// For exactly this reason, I'll exclude this component from our tests.

@Component({
	selector: "app-dialog-box",
	standalone: true,
	imports: [
		MatFormFieldModule,
		MatInputModule,
		FormsModule,
		MatButtonModule,
		MatDialogTitle,
		MatDialogContent,
		MatDialogActions,
		NgIf
	],
	templateUrl: "./dialog-box.component.html",
	styleUrl: "./dialog-box.component.css"
})
export class DialogBoxComponent {
	readonly dialogRef: MatDialogRef<DialogBoxComponent> = inject(MatDialogRef<DialogBoxComponent>);
	readonly data: { message: string; reason?: string } = inject<{ message: string; reason?: string }>(MAT_DIALOG_DATA);

	/**
	 * Closes the dialog box.
	 */
	protected closeBox(): void {
		this.dialogRef.close();
	}
}
