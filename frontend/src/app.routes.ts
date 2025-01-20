import { Routes } from "@angular/router";
import { LogInComponent } from "./components/pages/log-in/log-in.component";
import { PatientAnalyticsComponent } from "./components/pages/patient-analytics/patient-analytics.component";
import { PatientRegistrationComponent } from "./components/pages/patient-registration/patient-registration.component";
import { PatientListComponent } from "./components/pages/patient-list/patient-list.component";
import { PageNotFoundComponent } from "./components/pages/page-not-found/page-not-found.component";
import { StyleReferenceComponent } from "./components/pages/style-reference/style-reference.component";
import { authGuard } from "./guards/auth.guard";

export const routes: Routes = [
	{ path: "dev-style-test", title: "Not for public eyes", component: StyleReferenceComponent },
	{ path: "log-in", title: "ITM Login", component: LogInComponent },
	{
		path: "patient-portal",
		title: "ITM Analytics",
		component: PatientAnalyticsComponent,
		canActivate: [authGuard],
		data: { roles: ["medical_professional", "patient"] },
		children: [
			{
				path: "",
				redirectTo: "patient-analytics",
				pathMatch: "full"
			},
			{
				path: "patient-analytics",
				title: "ITM Analytics",

				component: PatientAnalyticsComponent
			}
		]
	},
	{
		path: "mp-portal",
		title: "ITM Medical Portal",
		canActivate: [authGuard],
		data: { roles: ["medical_professional"] },
		children: [
			{
				path: "",
				redirectTo: "patient-list",
				pathMatch: "full"
			},
			{
				path: "patient-list",
				title: "ITM Patient List",
				component: PatientListComponent
			},
			{
				path: "register-patient",
				title: "ITM Patient Registration",
				component: PatientRegistrationComponent
			},
			{
				path: "patient-analytics",
				title: "ITM Patient Analytics",
				component: PatientAnalyticsComponent
			}
		]
	},
	{ path: "page-not-found", title: "404 Page Not Found", component: PageNotFoundComponent },
	{ path: "", redirectTo: "log-in", pathMatch: "full" },
	{ path: "login", redirectTo: "log-in", pathMatch: "full" },
	{ path: "**", redirectTo: "page-not-found" }
];
