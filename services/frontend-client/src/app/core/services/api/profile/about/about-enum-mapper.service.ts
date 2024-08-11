import { Injectable } from '@angular/core';
import { RelationshipType } from "@enums/api/profile/about/relationship-type.enum";
import { Relationship } from "@enums/profile/relationship.enum";
import { GenderType } from "@enums/api/profile/about/gender-type.enum";
import { Gender } from "@enums/profile/gender.enum";

@Injectable({
    providedIn: 'root'
})
export class AboutEnumMapperService {

    constructor() {
    }

    mapRelationshipTypeToRelationship(relationship: string): Relationship {
        if (relationship === RelationshipType.SINGLE) {
            return Relationship.SINGLE;
        }
        if (relationship === RelationshipType.IN_A_RELATIONSHIP) {
            return Relationship.IN_A_RELATIONSHIP;
        }
        if (relationship === RelationshipType.ENGAGED) {
            return Relationship.ENGAGED;
        }
        if (relationship === RelationshipType.MARRIED) {
            return Relationship.MARRIED;
        }
        throw new Error('Invalid relationship');
    }

    mapRelationshipToTypeEnum(relationship: string): RelationshipType {
        if (relationship === Relationship.SINGLE) {
            return RelationshipType.SINGLE;
        }
        if (relationship === Relationship.IN_A_RELATIONSHIP) {
            return RelationshipType.IN_A_RELATIONSHIP;
        }
        if (relationship === Relationship.ENGAGED) {
            return RelationshipType.ENGAGED;
        }
        if (relationship === Relationship.MARRIED) {
            return RelationshipType.MARRIED;
        }
        throw new Error('Invalid relationship');
    }

    mapGenderToEnum(gender: string): GenderType {
        if (gender === Gender.MALE) {
            return GenderType.MALE;
        } else if (gender === Gender.FEMALE) {
            return GenderType.FEMALE;
        }
        throw new Error("Invalid gender");
    }

}
