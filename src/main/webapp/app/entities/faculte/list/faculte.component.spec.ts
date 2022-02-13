import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { FaculteService } from '../service/faculte.service';

import { FaculteComponent } from './faculte.component';

describe('Faculte Management Component', () => {
  let comp: FaculteComponent;
  let fixture: ComponentFixture<FaculteComponent>;
  let service: FaculteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FaculteComponent],
    })
      .overrideTemplate(FaculteComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FaculteComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FaculteService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.facultes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
