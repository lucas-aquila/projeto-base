import { Injectable } from '@angular/core';
import { Describer } from '../../application/describer/describer';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BASE_URL } from './base-url';


const url = `${BASE_URL}/setores`;

@Injectable()
export class SetorService {

    /**
     * 
     * @param http 
     */
    constructor(private http: HttpClient) {}

    /**
     * 
     * @param id 
     */
    public findSetorById(id: number): Promise<any> {
        return this.http.get(`${url}/${id}`).toPromise();
    }

    /**
     * 
     * @param filter 
     * @param status 
     * @param pageable 
     */
    public listSetoresByFilters(filter: any = '', status: any = '', pageable: any): Promise<any> {
        let params = new HttpParams();
        params = params.set('filter', filter || '');
        params = params.set('status', status);
        params = Describer.getHttpParamsFromPageable(params, pageable);

        return this.http.get(url, { params }).toPromise();
    }

    /**
     * 
     * @param setor 
     */
    public insertSetor(setor: any): Promise<any> {
        return this.http.post(url, setor).toPromise();
    }

    /**
     * 
     * @param setor 
     */
    public updateSetor(setor: any): Promise<any> {
        return this.http.put(`${url}/${setor.id}`, setor).toPromise();
    }

    /**
     * 
     * @param id 
     */
    public updateStatusSetor(id: number): Promise<any> {
        return this.http.put(`${url}/status/${id}`, null).toPromise();
    }

    /**
     * 
     * @param id 
     */
    public deleteSetor(id: number): Promise<any> {
        return this.http.delete(`${url}/${id}`).toPromise();
    }  
}
