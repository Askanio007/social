import {Action} from 'redux';
import {GroupRelation} from '../service/GroupService';

export enum GroupActions {
    GET_LIST_GROUP = 'LIST_GROUP',
    EXIT_GROUP = 'EXIT_GROUP',
    JOIN_GROUP = 'JOIN_GROUP',
    GET_GROUP = 'GET_GROUP'
}

export interface GetListAction extends Action<GroupActions.GET_LIST_GROUP>{
    list: any[]
    countRecord:number
    currentPage:number
}

export interface ExitFromGroupAction extends Action<GroupActions.EXIT_GROUP>{
    id:number;
}

export interface JoinGroupAction extends Action<GroupActions.JOIN_GROUP>{
    group:any;
}

export interface GetGroupAction extends Action<GroupActions.GET_GROUP>{
    group:any;
    countParticipant:number,
    groupRelation:GroupRelation
}

function join(group:any):JoinGroupAction {
    return {
        type: GroupActions.JOIN_GROUP,
        group
    }
}

function list(list:any, countRecord:number, currentPage:number):GetListAction {
    return {
        type: GroupActions.GET_LIST_GROUP,
        list,
        countRecord,
        currentPage
    }
}

function get(group:any, countParticipant:number, groupRelation:GroupRelation):GetGroupAction {
    return {
        type: GroupActions.GET_GROUP,
        group,
        countParticipant,
        groupRelation
    }
}

function exit(id:number):ExitFromGroupAction {
    return {
        type: GroupActions.EXIT_GROUP,
        id
    }
}

export {join, list, exit, get};
