/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { GraduationProjectService } from 'app/entities/graduation-project/graduation-project.service';
import { IGraduationProject, GraduationProject } from 'app/shared/model/graduation-project.model';

describe('Service Tests', () => {
  describe('GraduationProject Service', () => {
    let injector: TestBed;
    let service: GraduationProjectService;
    let httpMock: HttpTestingController;
    let elemDefault: IGraduationProject;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(GraduationProjectService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new GraduationProject(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a GraduationProject', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdTime: currentDate,
            modifyTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new GraduationProject(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a GraduationProject', async () => {
        const returnedFromService = Object.assign(
          {
            graduationProjectTitle: 'BBBBBB',
            source: 'BBBBBB',
            taskBook: 'BBBBBB',
            taskBookCheck: true,
            taskBookReviews: 'BBBBBB',
            openingReport: 'BBBBBB',
            openingReportCheck: true,
            openingReportReviews: 'BBBBBB',
            midTermInspection: 'BBBBBB',
            midTermInspectionCheck: true,
            midTermInspectionReviews: 'BBBBBB',
            firstDraft: 'BBBBBB',
            firstDraftCheck: true,
            firstDraftReviews: 'BBBBBB',
            finalDraft: 'BBBBBB',
            finalDraftCheck: true,
            finalDraftReviews: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdTime: currentDate,
            modifyTime: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of GraduationProject', async () => {
        const returnedFromService = Object.assign(
          {
            graduationProjectTitle: 'BBBBBB',
            source: 'BBBBBB',
            taskBook: 'BBBBBB',
            taskBookCheck: true,
            taskBookReviews: 'BBBBBB',
            openingReport: 'BBBBBB',
            openingReportCheck: true,
            openingReportReviews: 'BBBBBB',
            midTermInspection: 'BBBBBB',
            midTermInspectionCheck: true,
            midTermInspectionReviews: 'BBBBBB',
            firstDraft: 'BBBBBB',
            firstDraftCheck: true,
            firstDraftReviews: 'BBBBBB',
            finalDraft: 'BBBBBB',
            finalDraftCheck: true,
            finalDraftReviews: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdTime: currentDate,
            modifyTime: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GraduationProject', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
