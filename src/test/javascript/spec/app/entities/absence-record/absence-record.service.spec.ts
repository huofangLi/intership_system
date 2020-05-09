/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AbsenceRecordService } from 'app/entities/absence-record/absence-record.service';
import { IAbsenceRecord, AbsenceRecord } from 'app/shared/model/absence-record.model';

describe('Service Tests', () => {
  describe('AbsenceRecord Service', () => {
    let injector: TestBed;
    let service: AbsenceRecordService;
    let httpMock: HttpTestingController;
    let elemDefault: IAbsenceRecord;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AbsenceRecordService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AbsenceRecord(0, currentDate, currentDate, 0, 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            absenceStartTime: currentDate.format(DATE_TIME_FORMAT),
            absenceEndTime: currentDate.format(DATE_TIME_FORMAT),
            createdTime: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a AbsenceRecord', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            absenceStartTime: currentDate.format(DATE_TIME_FORMAT),
            absenceEndTime: currentDate.format(DATE_TIME_FORMAT),
            createdTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            absenceStartTime: currentDate,
            absenceEndTime: currentDate,
            createdTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new AbsenceRecord(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AbsenceRecord', async () => {
        const returnedFromService = Object.assign(
          {
            absenceStartTime: currentDate.format(DATE_TIME_FORMAT),
            absenceEndTime: currentDate.format(DATE_TIME_FORMAT),
            absenceDays: 1,
            company: 'BBBBBB',
            remarks: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            absenceStartTime: currentDate,
            absenceEndTime: currentDate,
            createdTime: currentDate
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

      it('should return a list of AbsenceRecord', async () => {
        const returnedFromService = Object.assign(
          {
            absenceStartTime: currentDate.format(DATE_TIME_FORMAT),
            absenceEndTime: currentDate.format(DATE_TIME_FORMAT),
            absenceDays: 1,
            company: 'BBBBBB',
            remarks: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            absenceStartTime: currentDate,
            absenceEndTime: currentDate,
            createdTime: currentDate
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

      it('should delete a AbsenceRecord', async () => {
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
