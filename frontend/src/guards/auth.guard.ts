import { CanActivateFn } from "@angular/router";
import { RoleService } from "../services/role/role.service";
import { inject } from "@angular/core";

/**
 * Guard to check if the user has the required roles to access a route.
 *
 * @param {import('@angular/router').ActivatedRouteSnapshot} route - The route that is being accessed.
 * @param {import('@angular/router').RouterStateSnapshot} state - The state of the router at the time of access.
 * @returns {boolean} - Returns true if the user is authorized, otherwise false.
 */
export const authGuard: CanActivateFn = (route, state) => {
	const roleService = inject(RoleService);
	const requiredRoles = route.data["roles"] as Array<string>;

	if (!requiredRoles) {
		return false;
	}

	return roleService.isAuthorized(requiredRoles);
};
