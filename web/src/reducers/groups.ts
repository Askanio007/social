import {Dispatch} from 'redux';
import {
    exit,
    ExitFromGroupAction,
    get,
    GetGroupAction,
    GetListAction,
    GroupActions,
    join,
    JoinGroupAction,
    list
} from '../actions/GroupActions';
import GroupService, {GroupRelation} from '../service/GroupService';

export interface GroupState {
    get: {
        group: any
        countParticipant: number
        groupRelation: GroupRelation
    }
    getList: {
        groups: any[]
        countRecord: number
        currentPage: number
    }
}

const initialState:GroupState = {
    get: {
        group: null,
        countParticipant: 0,
        groupRelation: GroupRelation.NOT_PARTICIPANT
    },
    getList: {
        groups: [],
        countRecord: 0,
        currentPage: 0
    }
};


const groupReducer = (state: GroupState = initialState, action:any): GroupState => {
    switch (action.type) {
        case GroupActions.JOIN_GROUP: {
            return {
                ...state,
                getList: {
                    ...state,
                    groups: [...state.getList.groups, action.group],
                    countRecord: state.getList.countRecord + 1,
                    currentPage: state.getList.currentPage
                }
            }
        }
        case GroupActions.GET_GROUP: {
            return {
                ...state,
                get: {
                    group: action.group,
                    countParticipant: action.countParticipant,
                    groupRelation: action.groupRelation

                }
            };
        }
        case GroupActions.GET_LIST_GROUP: {
            return {
                ...state,
                getList: {
                    groups: action.list,
                    countRecord: action.countRecord,
                    currentPage: action.currentPage,
                }
            };
        }
        case GroupActions.EXIT_GROUP: {
            let { getList } = state;
            return {
                ...state,
                getList: {
                    groups: [...getList.groups.filter(group => group.id !== action.id)],
                    countRecord: getList.countRecord - 1,
                    currentPage: getList.currentPage
                }
            }
        }
        default:
            return state;
    }
};

export function getList(page:number) {
    return async (dispatch: Dispatch<GetListAction>) => {
        try {
            const response = await GroupService.findListByRootUser(page);
            dispatch(list(response.data.data.content, response.data.data.totalElements, page));
        } catch (e) {
            throw new Error(e);
        }
    }
}

export function getGroup(id:number) {
    return async (dispatch: Dispatch<GetGroupAction>) => {
        try {
            const group = await GroupService.findAwait(id);
            const countParticipant = await GroupService.countParticipant(id);
            const groupRelation = await GroupService.groupRelation(id);
            dispatch(get(group.data.data, countParticipant.data.data, groupRelation.data.data));
        } catch (e) {
            throw new Error(e);
        }
    }
}

export function exitGroup(id:number) {
    return async (dispatch: Dispatch<ExitFromGroupAction>) => {
        try {
            await GroupService.exit(id);
            dispatch(exit(id));
        } catch (e) {
            throw new Error(e);
        }
    }
}

export function joinGroup(id:number) {
    return async (dispatch: Dispatch<JoinGroupAction>) => {
        try {
            await GroupService.join(id);
            let group = await GroupService.findAwait(id);
            dispatch(join(group));
        } catch (e) {
            throw new Error(e);
        }
    }
}

export default groupReducer;
