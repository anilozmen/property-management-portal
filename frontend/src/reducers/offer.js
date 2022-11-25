import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  isInProgress: false,
  data: [],
  propertyId: null,
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
    setPropertyId: (state, action) => {
      state.propertyId = action.payload;
    },
    setError: (state, action) => (state.error = action.payload),
  },
});

export const { setIsInProgress, setError, setData, setPropertyId } =
  offer.actions;

export const fetchOffersAsyncAction =
  (propertyId, successCallback, force = true) =>
  (dispatch, getState) => {
    const data = getState().offer.data;

    if (propertyId === getState().offer.propertyId && !force) {
      dispatch(setData([...data]));
      return;
    }

    dispatch(setPropertyId(propertyId));
    dispatch(setIsInProgress(true));

    axios
      .get(`/properties/${propertyId}/offers`)
      .then((response) => {
        if (typeof successCallback === "function") successCallback();
        dispatch(setData(response.data));
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
