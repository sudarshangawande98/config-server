-- Insert default users
INSERT INTO users (username, password, enabled) VALUES ('MARGIN_SERVICE_DEV_USER', '{noop}password', true);
INSERT INTO users (username, password, enabled) VALUES ('MARGIN_SERVICE_PROD_USER', '{noop}password', true);
INSERT INTO users (username, password, enabled) VALUES ('CIM_SERVICE_DEV_USER', '{noop}password', true);
INSERT INTO users (username, password, enabled) VALUES ('CIM_SERVICE_PROD_USER', '{noop}password', true);

-- Insert default roles for users WITHOUT the ROLE_ prefix
INSERT INTO authorities (username, authority) VALUES ('MARGIN_SERVICE_DEV_USER', 'DEV_C2N_MARGIN_SERVICE');
INSERT INTO authorities (username, authority) VALUES ('MARGIN_SERVICE_PROD_USER', 'PROD_C2N_MARGIN_SERVICE');
INSERT INTO authorities (username, authority) VALUES ('CIM_SERVICE_DEV_USER', 'DEV_C2N_CIM_SERVICE');
INSERT INTO authorities (username, authority) VALUES ('CIM_SERVICE_PROD_USER', 'PROD_C2N_CIM_SERVICE');
