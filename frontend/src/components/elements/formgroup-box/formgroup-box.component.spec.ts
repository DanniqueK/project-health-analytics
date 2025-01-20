import { ComponentFixture, TestBed } from "@angular/core/testing";

import { FormgroupBoxComponent } from "./formgroup-box.component";

describe("FormgroupBoxComponent", () => {
	let component: FormgroupBoxComponent;
	let fixture: ComponentFixture<FormgroupBoxComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [FormgroupBoxComponent]
		}).compileComponents();

		fixture = TestBed.createComponent(FormgroupBoxComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});
});
