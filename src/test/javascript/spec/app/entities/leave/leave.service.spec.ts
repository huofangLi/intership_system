/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LeaveService } from 'app/entities/leave/leave.service';
import { ILeave, Leave } from 'app/shared/model/leave.model';

describe('Service Tests', () => {
  describe('Leave Service', () => {
    let injector: TestBed;
    let service: LeaveService;
    let httpMock: HttpTestingController;
    let elemDefault: ILeave;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LeaveService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Leave(0, currentDate, currentDate, 0, 'AAAAAAA', 'AAAAAAA', false, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            submitTime: currentDate.format(DATE_TIME_FORMAT),
            leaveTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Leave', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            submitTime: currentDate.format(DATE_TIME_FORMAT),
            leaveTime: currentDate.format(DATE_TIME_FORMAT),
            createdTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            submitTime: currentDate,
            leaveTime: currentDate,
            createdTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new Leave(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Leave', async () => {
        const returnedFromService = Object.assign(
          {
            submitTime: currentDate.format(DATE_TIME_FORMAT),
            leaveTime: currentDate.format(DATE_TIME_FORMAT),
            leaveDays: 1,
            company: 'BBBBBB',
            leaveReason: 'BBBBBB',
            status: true,
            createdTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            submitTime: currentDate,
            leaveTime: currentDate,
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

      it('should return a list of Leave', async () => {
        const returnedFromService = Object.assign(
          {
            submitTime: currentDate.format(DATE_TIME_FORMAT),
            leaveTime: currentDate.format(DATE_TIME_FORMAT),
            leaveDays: 1,
            company: 'BBBBBB',
            leaveReason: 'BBBBBB',
            status: true,
            createdTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            submitTime: currentDate,
            leaveTime: currentDate,
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

      it('should delete a Leave', async () => {
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
