import "./Footer.css";
// import { GettoKnowUs, ConnectwithUs, MakeMoneywithUs} from "../Details/FooterDetail";
import { Link } from "react-router-dom";

const GettoKnowUs = [
  'About Us',
 
];

const ConnectwithUs = [
  'Facebook',
  'Twitter',
  'Instagram',
  'LinkedIn'
];

const MakeMoneywithUs = [
  'Sell on PharmaEase',
  'Become an Affiliate',
  'Advertise Your Products',
  'Self-Publish with Us'
];



const Footer = () => {
  return (
    <div id="footer-container">
      <div className="btn" style={{padding: '1rem',marginTop:'1rem'}} onClick={() => { window.scrollTo({ top : 0, behavior: 'smooth' }) }}>Back to top</div>
      <div className="footer-section">
        <div className="footer-menu">
         <FooterUiLi heading='Get to Know Us' FootertInfom={GettoKnowUs}/>
         <FooterUiLi heading='Connect with Us' FootertInfom={ConnectwithUs}/>
         {localStorage.getItem("role")=="CUSTOMER" &&<FooterUiLi heading='Make Money with Us' FootertInfom={MakeMoneywithUs}/>}
        </div>
      </div>
      <div className="saperater"></div>
      <div className="footer-section top-bottom-padding flexBox">
      <div className="logo">
            <Link to={'/'}>
               <img className="image" src="./image/Print.svg" alt="" />
            </Link>
      </div>
      
      </div>
      
    </div>
  );
};

export default Footer;

export const FooterUiLi = (props) => {
  const { heading, FootertInfom } = props;
  return (
    <>
      <ul>
        <h4>{heading}</h4>
        {FootertInfom.map((item,index) => (
          <li key={`${item}-${index}`}>{item}</li>
        ))}
      </ul>
     </>
  );
};
