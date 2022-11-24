import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  isInProgress: false,
  data: [],
  error: null,
};

const offer = createSlice({
  name: "offer",
  initialState,
  reducers: {
    setIsInProgress: (state, action) => {
      state.isInProgress = !!action.payload;
    },
    setData: (state, action) => {
      state.data = action.payload;
    },
    setError: (state, action) => (state.error = action.payload),
  },
});

export const { setIsInProgress, setError, setData } = offer.actions;

export const fetchOffersAsyncAction =
  (propertyId, successCallback) => (dispatch) => {
    dispatch(setIsInProgress(true));

    axios
      .get(`/properties/${propertyId}/offers`)
      .then((response) => {
        dispatch(setData(response.data));
        if (typeof successCallback === "function") successCallback();
      })
      .catch((error) => {
        console.log(error);
        dispatch(setError(error));
      })
      .finally(() => {
        dispatch(setIsInProgress(false));
      });
  };


export default offer.reducer;
