/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AttendanceManagementService } from 'app/entities/attendance-management/attendance-management.service';
import { IAttendanceManagement, AttendanceManagement } from 'app/shared/model/attendance-management.model';

describe('Service Tests', () => {
  describe('AttendanceManagement Service', () => {
    let injector: TestBed;
    let service: AttendanceManagementService;
    let httpMock: HttpTestingController;
    let elemDefault: IAttendanceManagement;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AttendanceManagementService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AttendanceManagement(0, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            signIn: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a AttendanceManagement', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            signIn: currentDate.format(DATE_TIME_FORMAT),
            createdTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            signIn: currentDate,
            createdTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new AttendanceManagement(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AttendanceManagement', async () => {
        const returnedFromService = Object.assign(
          {
            signIn: currentDate.format(DATE_TIME_FORMAT),
            position: 'BBBBBB',
            coordinate: 'BBBBBB',
            company: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            signIn: currentDate,
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

      it('should return a list of AttendanceManagement', async () => {
        const returnedFromService = Object.assign(
          {
            signIn: currentDate.format(DATE_TIME_FORMAT),
            position: 'BBBBBB',
            coordinate: 'BBBBBB',
            company: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            signIn: currentDate,
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

      it('should delete a AttendanceManagement', async () => {
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
