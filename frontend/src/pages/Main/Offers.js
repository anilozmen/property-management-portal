import axios from "axios";
import { useEffect, useState } from "react";
import CustomerOffer from "../../components/CustomerOffer/CustomerOffer";
import ContentContainer from "../../components/Layout/Layout";

const Offers = () => {
  const [myOffers, setMyOffers] = useState([]);

  useEffect(() => {
    axios
      .get("/offers")
      .then((response) => {
        if (response.status === 200) {
          setMyOffers(response.data);
        } else {
          alert("API ERROR.");
        }
      })
      .catch((e) => {
        console.log(e);
        alert("API ERROR.");
      });
  }, []);

  return (
    <ContentContainer>
      <div class="title-box-d section-t4">
        <h3 class="title-d">My Offers</h3>
      </div>

      {myOffers.map((offer) => (
        <CustomerOffer key={offer.id} offer={offer} />
      ))}
    </ContentContainer>
  );
};

export default Offers;
