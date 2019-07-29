import {applyMiddleware, combineReducers, createStore, Store} from 'redux';
import ReduxThunk from 'redux-thunk';
import groupReducer, {GroupState} from './reducers/groups';
import {composeWithDevTools} from 'redux-devtools-extension';

const rootReducer = combineReducers({
    groups: groupReducer
});

export interface IStore {
    groups: GroupState
}

const middlewares = [
    ReduxThunk
];

const configureStore = (initialState?:IStore): Store<IStore> => (
    createStore(rootReducer, initialState,  composeWithDevTools(applyMiddleware(...middlewares)))
);

const store = configureStore();

export default store;
