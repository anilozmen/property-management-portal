import {createSlice} from "@reduxjs/toolkit";
import axios from "axios";
import {setTokens} from "../services/token";

const initialState = {
    isLoggingIn: false,
    error: null
};

const login = createSlice({
    name: 'login',
    initialState,
    reducers: {
        setIsLoggingin: (state, action) => {
            state.isLoggingIn = !!action.payload;
        },
        error: (state, action) => {
            state.error = action.payload;
        }
    }
});

const {setIsLoggingin, error} = login.actions;

export const loginAsyncAction = (loginData, successCallback) => (dispatch) => {
    dispatch(setIsLoggingin(true));

    axios.post('/authenticate/login', loginData)
        .then(response => {
            const {accessToken, refreshToken, userType} = response.data;
            setTokens({accessToken, refreshToken, userType});
            successCallback();
        })
        .catch(err => {
            console.log(err); 
            dispatch(error(err.response.data.error.message));
            alert(err.response.data.error.message);
        })
        .finally(() => {
            dispatch(setIsLoggingin(false));
        });
}

export default login.reducer;
