import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'participant',
        data: { pageTitle: 'area01ApplicationApp.participant.home.title' },
        loadChildren: () => import('./participant/participant.module').then(m => m.ParticipantModule),
      },
      {
        path: 'inscription',
        data: { pageTitle: 'area01ApplicationApp.inscription.home.title' },
        loadChildren: () => import('./inscription/inscription.module').then(m => m.InscriptionModule),
      },
      {
        path: 'faculte',
        data: { pageTitle: 'area01ApplicationApp.faculte.home.title' },
        loadChildren: () => import('./faculte/faculte.module').then(m => m.FaculteModule),
      },
      {
        path: 'groupe',
        data: { pageTitle: 'area01ApplicationApp.groupe.home.title' },
        loadChildren: () => import('./groupe/groupe.module').then(m => m.GroupeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
