import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFaculte } from '../faculte.model';
import { FaculteService } from '../service/faculte.service';
import { FaculteDeleteDialogComponent } from '../delete/faculte-delete-dialog.component';

@Component({
  selector: 'jhi-faculte',
  templateUrl: './faculte.component.html',
})
export class FaculteComponent implements OnInit {
  facultes?: IFaculte[];
  isLoading = false;

  constructor(protected faculteService: FaculteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.faculteService.query().subscribe({
      next: (res: HttpResponse<IFaculte[]>) => {
        this.isLoading = false;
        this.facultes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFaculte): number {
    return item.id!;
  }

  delete(faculte: IFaculte): void {
    const modalRef = this.modalService.open(FaculteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.faculte = faculte;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
