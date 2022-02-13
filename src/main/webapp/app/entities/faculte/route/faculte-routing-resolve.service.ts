import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFaculte, Faculte } from '../faculte.model';
import { FaculteService } from '../service/faculte.service';

@Injectable({ providedIn: 'root' })
export class FaculteRoutingResolveService implements Resolve<IFaculte> {
  constructor(protected service: FaculteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFaculte> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((faculte: HttpResponse<Faculte>) => {
          if (faculte.body) {
            return of(faculte.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Faculte());
  }
}
