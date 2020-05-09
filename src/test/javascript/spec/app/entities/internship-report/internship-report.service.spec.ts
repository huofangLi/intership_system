/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InternshipReportService } from 'app/entities/internship-report/internship-report.service';
import { IInternshipReport, InternshipReport } from 'app/shared/model/internship-report.model';

describe('Service Tests', () => {
  describe('InternshipReport Service', () => {
    let injector: TestBed;
    let service: InternshipReportService;
    let httpMock: HttpTestingController;
    let elemDefault: IInternshipReport;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InternshipReportService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InternshipReport(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createTime: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a InternshipReport', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new InternshipReport(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a InternshipReport', async () => {
        const returnedFromService = Object.assign(
          {
            reportType: 'BBBBBB',
            projectName: 'BBBBBB',
            intershipStage: 'BBBBBB',
            annex: 'BBBBBB',
            reportContent: 'BBBBBB',
            createTime: currentDate.format(DATE_TIME_FORMAT),
            star: 'BBBBBB',
            status: 'BBBBBB',
            operating: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createTime: currentDate
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

      it('should return a list of InternshipReport', async () => {
        const returnedFromService = Object.assign(
          {
            reportType: 'BBBBBB',
            projectName: 'BBBBBB',
            intershipStage: 'BBBBBB',
            annex: 'BBBBBB',
            reportContent: 'BBBBBB',
            createTime: currentDate.format(DATE_TIME_FORMAT),
            star: 'BBBBBB',
            status: 'BBBBBB',
            operating: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createTime: currentDate
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

      it('should delete a InternshipReport', async () => {
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
