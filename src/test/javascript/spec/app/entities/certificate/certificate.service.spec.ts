/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CertificateService } from 'app/entities/certificate/certificate.service';
import { ICertificate, Certificate } from 'app/shared/model/certificate.model';

describe('Service Tests', () => {
  describe('Certificate Service', () => {
    let injector: TestBed;
    let service: CertificateService;
    let httpMock: HttpTestingController;
    let elemDefault: ICertificate;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CertificateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Certificate(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            certificateAcquisitionTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Certificate', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            certificateAcquisitionTime: currentDate.format(DATE_TIME_FORMAT),
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            certificateAcquisitionTime: currentDate,
            createdTime: currentDate,
            modifyTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new Certificate(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Certificate', async () => {
        const returnedFromService = Object.assign(
          {
            certificateName: 'BBBBBB',
            certificateType: 'BBBBBB',
            certificateLevel: 'BBBBBB',
            certificateAcquisitionTime: currentDate.format(DATE_TIME_FORMAT),
            certificatePhoto: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            certificateAcquisitionTime: currentDate,
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

      it('should return a list of Certificate', async () => {
        const returnedFromService = Object.assign(
          {
            certificateName: 'BBBBBB',
            certificateType: 'BBBBBB',
            certificateLevel: 'BBBBBB',
            certificateAcquisitionTime: currentDate.format(DATE_TIME_FORMAT),
            certificatePhoto: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            certificateAcquisitionTime: currentDate,
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

      it('should delete a Certificate', async () => {
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
