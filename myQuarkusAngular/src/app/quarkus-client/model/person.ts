/**
 * Generated API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


export interface Person { 
    id?: number;
    birth?: string;
    name?: string;
    status?: Person.StatusEnum;
}
export namespace Person {
    export type StatusEnum = 'Alive' | 'DECEASED';
    export const StatusEnum = {
        Alive: 'Alive' as StatusEnum,
        DECEASED: 'DECEASED' as StatusEnum
    };
}


