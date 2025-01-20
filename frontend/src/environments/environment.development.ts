export const environment = {
	API: "http://localhost:80",
	MESSAGES: {
		NO_PATIENT_SELECTED: "No Patient Selected",
		NO_PATIENT_SELECTED_ERROR: "No patient selected. Please select a patient from the patient list.",
		NO_PATIENT_FOUND: "No patients found, please enter a valid name or BSN",
		NO_ASSIGNED_PATIENTS: "No patients assigned to you, please search for the required patient's name or BSN.",
		FILL_OUT_FIELDS: "Please fill out all the fields",
		INVALID_EMAIL_BSN: "Invalid email or bsn!",
		SUCCESS_REDIRECT: "Success, You are being redirected!",
		ENTER_PATIENT_NAME: "Please enter a patient name to search for",
		ERROR_FETCH_PATIENT: "Error fetching patients:",
		ERROR_FETCH_PATIENT_MESSAGE: "An error occurred while fetching the patients.",
		ERROR_SEARCH_PATIENT: "Error searching patients:",
		ERROR_FETCH_HEALTH: "Error fetching health data:",
		NO_HEALTH_DATA_FOUND: "Patient has no health data."
	},
	EXCLUDE_FIELDS: ["dataId", "patientBsn", "dateOfCollection"]
};
